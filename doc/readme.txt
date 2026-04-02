##server.xml 컨텍스트추가
<Context docBase="C:\MOEF\data\upload\web" path="/DATA/upload/web" />
<Context docBase="C:\MOEF\data\mov" path="/DATA/mov" />
----------------------------------------------------------------------------
##이클립스 톰캣 환경변수
-Dserver.mode="local"
-Dcom.dsjdf.config.file="C:/MOEF/gpkiapi/conf/dsjdf.properties"
----------------------------------------------------------------------------
##gpki설치방법
소스/doc/gpki/gpkiapi.zip파일을
C:/MOEF/gpkiapi폴더에 압축풀어야함
----------------------------------------------------------------------------

##운영서버 환경변수 프로파일
JAVA_HOME=/usr/lib/jvm/java-1.8.0-openjdk-1.8.0.362.b09-2.el8_7.x86_64
export JAVA_HOME
PATH=$PATH:$JAVA_HOME/bin
export PATH
export CATALINA_OUT="/dev/null"
----------------------------------------------------------------------------

##운영서버 톰캣
JAVA_OPTS="$JAVA_OPTS -Djava.awt.headless=true -Dfile.encoding=UTF-8 -server -Xms4092M -Xmx4092M -XX:NewSize=512m -XX:MaxNewSize=512m -XX:PermSize=512M -XX:MaxPermSize=512M -XX:+DisableExplicitGC"
JAVA_OPTS="$JAVA_OPTS -Dserver.mode=prod"

##기재부 서버구성현황
web: 2식
was: 2식
db: 2식

