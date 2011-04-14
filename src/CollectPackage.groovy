ant = new AntBuilder();

def destpath = CollectConfig.pathPackages

new File(destpath).deleteDir()
new File(destpath).mkdirs()

new File(CollectConfig.pathPackages + "android.jar").delete();
new File(CollectConfig.pathPackages + "sources.zip").delete();

incl = "**/*.class, **/*.arsc, **/*.xml"

ant.zip(baseDir:CollectConfig.pathOutput, destFile:CollectConfig.pathPackages + "android.jar", includes:incl,
	update:true)

incl = "**/*.java, **/*.aidl, **/*.xml"

ant.zip(baseDir:CollectConfig.pathOutput, destFile:CollectConfig.pathPackages + "sources.zip", includes:incl,
	update:true)

CollectConfig.notification "Packages android.jar and sources.zip are in "+CollectConfig.pathPackages
