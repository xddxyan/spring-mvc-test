SRC=../js
Script=./script
JAR=compiler-latest/closure-compiler-v20160911.jar
if [ "$1" = "install" ]; then
	cnpm install --save-dev babel-cli
	cnpm install --save-dev babel-preset-es2015
elif [ "$1" = "build" ]; then
	babel $SRC -d $Script -w
elif [ "$1" = "dev" ]; then
  sed -i '' 's/model.jsp/model-dev.jsp/' ../WEB-INF/decorators.xml
elif [ "$1" = "publish" ]; then
  sed -i '' 's/model-dev.jsp/model.jsp/' ../WEB-INF/decorators.xml
  java -jar $JAR --js=$Script'/meta-**.js' $Script'/vue-**.js' --js=$Script'/management.js' $Script'/service-center.js' --js_output_file ../script/all.js
else
 echo "input install,build"
fi
