if [ "$1" = "install" ]; then
	cnpm install --save-dev babel-cli
	cnpm install --save-dev babel-preset-es2015
elif [ "$1" = "build" ]; then
	babel ~/tmpsrc -d ../script -w
else
 echo "input install,build"
fi