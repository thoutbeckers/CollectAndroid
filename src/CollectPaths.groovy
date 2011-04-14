
rootdirs = CollectConfig.rootdirs

def javapath = CollectConfig.pathPaths+CollectConfig.pathsJava
def androidpath = CollectConfig.pathPaths+CollectConfig.pathsAndroid
new File(javapath).delete();
new File(androidpath).delete();
def jp = 0;
def ap = 0;

rootdirs.each {
	
	javaPaths = new HashSet()
	androidPaths = new HashSet()
	
	println "processing $it" 
	
 	def rootpath = CollectConfig.pathAndroid + it;  
	
	if ( new File(rootpath+"AndroidManifest.xml").exists() ) {
		println "Android (root) path added: $rootpath"
		androidPaths.add rootpath
	}
	
	def rootdir = new File(rootpath);
	
	rootdir.eachDirRecurse { 
		File dir ->
		
		// These are the additional projects we have to build with CollectAAPT
		dir.eachFileMatch "AndroidManifest.xml", { 
			println "Android path added: $dir"
			androidPaths.add dir.getPath()
		}
		
		for (File f: dir.listFiles()) {
			if (f.getName().endsWith(".java") || f.getName().endsWith(".aidl")) { 		
				BufferedReader r
				def recurseBack
				try {
					println "java: $f";
					r = f.newReader();
					while(true) {
						l = r.readLine()
						if (l == null) throw new IOException("End of file reached")
						//println "scanning line: $l"
						b = l.indexOf("package")
						e = l.indexOf(';')
						if (b != -1 && e != -1) {
							packageName = l.substring( b + 8, l.indexOf(';'))
							recurseBack = packageName.split("\\.").size()						
							print "java file; found package: $packageName, must recurse back: $recurseBack from ";
							println dir.getPath();
							break
						}
					}
				
				} catch (IOException e) {
					// we did not find a line with package
					println("java file; found no package")
					recuseBack = 0;
				}
				finally {
					def File dirToAdd = dir
					while (recurseBack>0) {
						dirToAdd = dirToAdd.getParentFile()
						recurseBack--
					}
					
					
					addpath = dirToAdd.getPath();
					final blacklist = [ "layoutlib", "hosttests"]
					blacklisted = false;
					blacklist.each { 
						if (!blacklisted)
							blacklisted = addpath.contains(it) 
						}
					
					
					if (blacklisted) {
						println("Java path refused $dirToAdd")
					}
					else {
						println("Java path added $dirToAdd")
						javaPaths.add dirToAdd.getPath()
					}
					
					try { r.close() } catch (Exception ignore) {}
				}
				
				break
			}
		}
	}
	
	jp+=javaPaths.size();
	ap+=androidPaths.size();
	
	paths=""
	javaPaths.collect {paths+=it+"\n" }
	new File(javapath).append paths
	
	paths=""
	androidPaths.collect {paths+=it+"\n" }
	new File(androidpath).append paths

}
String message = "collectpaths: found $jp java paths \nfound $ap android paths";
println message
CollectConfig.notification message
