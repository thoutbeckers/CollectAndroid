exec = ["java", "-Xmx3024m", "-jar", CollectConfig.pathECJ, "-bootclasspath",
	"\""+CollectConfig.pathSDK+"android.jar\"",
	"-maxProblems", "999999", "-5", "-nowarn", "-verbose", "-log", CollectConfig.pathTemp+"buildlog", "-progress", "-proceedOnError", "-time", 
	CollectConfig.pathOutput]

p = exec.execute();
p.consumeProcessOutput(System.out, System.out)
p.waitFor();

message = "compiled files"
CollectConfig.notification message
