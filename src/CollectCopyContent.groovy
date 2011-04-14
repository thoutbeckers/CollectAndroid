// clean directory
def destpath = CollectConfig.pathOutput

new File(destpath).deleteDir()
new File(destpath).mkdirs()

def warning = 0; def copies = 0; def created = 0;

new File(CollectConfig.pathPaths+CollectConfig.pathsJava).eachLine { 
	dir = new File(it)
	rootpath = dir.path;
	rlen = rootpath.length()
	dir.eachFileRecurse { File f ->
			subpath = f.getPath().substring(rlen)
			copyf = new File(destpath+subpath)
			if (f.isDirectory()) { 
				
				if (!f.getPath().contains("/.")) {
					copyf.mkdirs()
					println "Created path: "+copyf+ " rootpath: "+rootpath+" subpath: "+subpath
					created++
				}
			}				
			else {
				if (copyf.exists()) {
					println "WARNING; duplicate file! "+f.path
					warning++
				}
				else if (copyf.getPath().endsWith(".java") || copyf.getPath().endsWith(".aidl")) 
				{
					copyf << f.asWritable()
					println "Copied file: "+copyf
					copies++
				}
			}			
	}
}

message = "$warning warnings! $copies files copied. $created directories created"
println message
CollectConfig.notification message
