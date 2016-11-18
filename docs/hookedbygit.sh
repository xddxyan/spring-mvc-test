BASEDIR=$(dirname "$0")
echo '>>>>>>' $BASEDIR
if [ $BASEDIR != "." ]; then
	echo please run in directory of the shell
	exit
fi
GitHead=git-head
#git pull origin master
if [ -f "$GitHead" ]
then
	PreHead=`cat $GitHead`
else
	PreHead=foobar
fi
CurHead=`git rev-parse HEAD`
CurCommitMsg=`git show -s --format=%B`
echo '>>>>>>' $CurCommitMsg
echo '>>>>>>' $PreHead '>>>' $CurHead
if [ $PreHead != $CurHead ] && [[ $CurCommitMsg == *"#rebuild"* ]]; then
	./launch.sh rebuild
fi
echo $CurHead > $GitHead
if [[ $CurCommitMsg == *"#deploy"* ]]; then
	./launch.sh start
fi