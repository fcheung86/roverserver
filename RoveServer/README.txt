=======================
Steps to run RoveServer
=======================

Download the following
----------------------
1. Jetty Eclipse Plugin - http://run-jetty-run.googlecode.com/svn/trunk/updatesite
2. Maven - http://apache.sunsite.ualberta.ca/maven/maven-3/3.0.4/binaries/apache-maven-3.0.4-bin.zip
3. MySQL - http://www.mysql.com/downloads/mysql/
4. MySQL Workbench (GUI Tool) [Optional] - http://www.mysql.com/downloads/workbench/



Installing Jetty Eclipse Plugin
-------------------------------
1. Help > Install New Software...
2. Add...
3. Name = Jetty, Location = http://run-jetty-run.googlecode.com/svn/trunk/updatesite
4. Install RunJettyRun (You can't install both RunJettyRun and RunJettyRun 1.3 at the same time)


Installing Maven
----------------
1. Extract zip file somewhere, eg. D:\Development, you should now have D:\Development\apache-maven-3.0.4
2. Add bin directory to your PATH 
	a. Computer > System Properties > Advanced System Settings > Environment Variables
	b. In System variables, click on "PATH" and Edit...
	c. Add D:\Development\apache-maven-3.0.4\bin to the end
3. Set your JAVA_HOME
	a. Computer > System Properties > Advanced System Settings > Environment Variables
	b. In System variables, click on New...
	c. Variable name = JAVA_HOME, Variable value = eg. C:\Program Files (x86)\Java\jdk1.6.0_35
4. Go to command prompt and verify Maven is installed by running "mvn --version"


Installing MySQL/Workbench
--------------------------
// TODO


Running RoveServer
------------------
1. If everything is installed correctly, the project should build without a problem after importing from bitbucket
2. Open com.fourkins.rove.handler.PostHandler.java
3. Set a breakpoint on Line22 (Post post = PostProvider.getInstance().getPost(postId);)
4. Right click on the RoveServer Project > Debug As > Run Jetty
5. The server should now be running
6. Go to your browser and go to http://localhost:8080/RoveServer/posts/1
7. It should hit your breakpoint and if you resume, it should return whatever is entered in your MySQL database
