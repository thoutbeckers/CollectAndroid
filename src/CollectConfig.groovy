
class CollectConfig {

	// !!! make sure for directories you include the trailing slash
	
	// location of the android repo
	static pathAndroid = "/android/repo/"
	
	// path where source files are collected and compilation is done. Warning: this directories will be deleted whenever the scripts are run
	static pathOutput = "/temp/collectandroid/"
	
	// dir to store the files containing the paths in 
	static pathPaths = "/temp/"
	// file name for files storing the collected paths
	static pathsJava = "javapaths"
	static pathsAndroid = "androidpaths"
	
	// path to android SDK for the platform you are trying to build
	static pathSDK = "/android/android-sdk-mac/platforms/android-10/"
	
	// path to the platform tools directory 
	static pathTools = "/android/android-sdk-mac/platform-tools/"
	
	// path to temp dir
	static pathTemp = "/temp/"
	
	// path to jar file of Eclipse Java compilers
	static pathECJ = "/android/repo/prebuilt/common/ecj/ecj.jar"
	
	// path to store packages in
	static pathPackages = "/temp/androidpackages/"
	
	// android repo subdirectories to scan for paths  (includes subdirectories)
	static rootdirs = ["libcore/" , "packages/apps/Settings/", "frameworks/", "packages/apps/" ]
	
	// option to pass to the JVM when compiling. 
	// Example sets memory to max 3GB. The Eclipse compiler loves having as much as possible, but try with less if your machine does not have a lot of RAM 	
	static jvmOption = "-Xmx3024m"
	
	// handle notifications of progress. Put your own code for notifying yourself here
	static notification ( message ) {
		// eg just do a println to the shell:
		println("CollectAndroid: $message")
		
		// I use growlnotify to put up a sticky growl notification. Make sure you comment this out if you don't have growlnotify installed 			
		
		try {
			String[] notify =  ["/usr/local/bin/growlnotify", "-s", "-m", "\"$message\"", "CollectAndroid"]
			Runtime.getRuntime().exec notify
		} catch (Exception ignored) {}
	}
}
