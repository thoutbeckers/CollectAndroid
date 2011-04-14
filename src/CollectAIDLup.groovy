destpath = new File(CollectConfig.pathOutput)
aidl = CollectConfig.pathTools+"/aidl"

destpath.eachFileRecurse { File f ->
	if (f.isFile() && f.getName().endsWith(".aidl")) {
		println "aidling file: "+f
		aidlexec = [ aidl, "-I"+destpath.getPath(), f.getPath() ]

		p = aidlexec.execute();
		p.consumeProcessOutput(System.out, System.out)
		p.waitFor();

	}
}

message = "aidled files"
CollectConfig.notification message
