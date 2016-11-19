#export JAVA_HOME=/opt/jdk1.8.0_101
#export PATH=$PATH:$JAVA_HOME/bin
export M2_HOME=/c/ide/apache-maven-3.2.3/
export PATH=$PATH:$M2_HOME/bin
export TOMCAT_HOME=/opt/apache-tomcat-7.0.70
export CATALINA_HOME=$TOMCAT_HOME
export JAVA_OPTS="$JAVA_OPTS -Djava.security.egd=file:/dev/./urandom"

WebApp=webapps
App=data-analysis

func_compile()
{
#-pl, --projects
#        Build specified reactor projects instead of all projects
#-am, --also-make
#        If project list is specified, also build projects required by the list
	rm -Rf sd-model/target
	mvn install -pl sd-model -am war:war
}

func_deploy()
{
	mv target/$App.war $TOMCAT_HOME/$WebApp/ROOT.war
	rm -Rf $TOMCAT_HOME/$WebApp/ROOT
}

func_start()
{
	func_shut_tomcat
	$TOMCAT_HOME/bin/startup.sh
}

func_log()
{
	tail -f $TOMCAT_HOME/logs/catalina.out
}

func_shut_tomcat()
{
	$TOMCAT_HOME/bin/shutdown.sh
}

if [ "$1" = "start" ]; then
	func_start
elif [ "$1" = "rebuild" ]; then
	func_compile
elif [ "$1" = "log" ]; then
	func_log
elif [ "$1" = "deploy" ]; then
	func_deploy
elif [ "$1" = "shut" ]; then
	func_shut_tomcat
fi