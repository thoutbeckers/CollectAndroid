// make sure ant-launcher.jar and ant.jar are on the classpath using this script

def aapt = CollectConfig.pathTools + "aapt" 
def destpath = CollectConfig.pathOutput

String pathIgnore = CollectConfig.pathTemp+"ignore.apk"
String pathPublic = CollectConfig.pathTemp+"public.apk"

new File(pathIgnore).delete();
new File(pathPublic).delete();

new File(CollectConfig.pathPaths + CollectConfig.pathsAndroid).eachLine {
	
	if (new File("$it/res").exists()) {
		println "start with $it"
		
		aaptexec = [aapt, "package", "-f", "-M", "$it/AndroidManifest.xml", 
		           "-S", "$it/res",
		           "-m",
				   "-c", "en",		          
		           "-J",  CollectConfig.pathOutput]
		           
		boolean collectResource = false
		
		if (!it.contains("/core/res")) {
			aaptexec.add "-F"
			aaptexec.add pathIgnore
			aaptexec.add "-I"
			aaptexec.add CollectConfig.pathSDK+"android.jar"
			
		}
		else {
			println "Using public options!"
			// save the public resources to extract the resources.arsc file later.
			aaptexec.add "-F"
			aaptexec.add pathPublic
			aaptexec.add "-x"

			aaptexec.add "-P"
			aaptexec.add CollectConfig.pathOutput+"public"
			collectResource = true
		}

		           
		p = aaptexec.execute();
		p.consumeProcessOutput(System.out, System.out)
		p.waitFor();
		
		if (collectResource) {
			def ant = new AntBuilder()
			
			ant.unzip(  src:CollectConfig.pathTemp+"public.apk", 
				dest:CollectConfig.pathOutput, 
				overwrite:"true" )
			
			CollectConfig.notification "Collected public resources"
		}
		
		println "done with $it"
	}
	else 
		println "skipping $it (no res)"
}

message = "ran aapt (copied asrc file)"
CollectConfig.notification message
                  
