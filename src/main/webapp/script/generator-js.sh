JAR=compiler-latest/closure-compiler-v20160911.jar
ES6SRC=../../../../documents/es6/src/
if [ "$1" = "dev" ]; then
  sed -i '' 's/model.jsp/model-dev.jsp/' ../WEB-INF/decorators.xml
else
  sed -i '' 's/model-dev.jsp/model.jsp/' ../WEB-INF/decorators.xml
  java -jar $JAR --js='../js/meta-**.js' $ES6SRC'vue-**.js' --js=$ES6SRC'management.js' $ES6SRC'service-center.js' --js_output_file all.js
fi
