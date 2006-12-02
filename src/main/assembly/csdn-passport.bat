:: Generate axis client source for csdn passport by the wsdl.

set wsdlUrl=http://passport.csdn.net/WebService/UserLoginService.asmx?WSDL
set M2_REPO=%USERPROFILE%/.m2/repository
set cp=%M2_REPO%/axis/axis/1.3/axis-1.3.jar
set cp=%cp%;%M2_REPO%/commons-logging/commons-logging/1.1/commons-logging-1.1.jar
set cp=%cp%;%M2_REPO%/commons-discovery/commons-discovery/0.2/commons-discovery-0.2.jar
set cp=%cp%;%M2_REPO%/javax/xml/jaxrpc/1.1/jaxrpc-1.1.jar
set cp=%cp%;%M2_REPO%/axis/axis-jaxrpc/1.3/axis-jaxrpc-1.3.jar
set cp=%cp%;%M2_REPO%/axis/axis-saaj/1.3/axis-saaj-1.3.jar
set cp=%cp%;%M2_REPO%/javax/activation/activation/1.0.2/activation-1.0.2.jar
set cp=%cp%;%M2_REPO%/javax/mail/mail/1.3.2/mail-1.3.2.jar
set cp=%cp%;%M2_REPO%/axis/axis-wsdl4j/1.5.1/axis-wsdl4j-1.5.1.jar
echo %cp%
cd ../../../target/
:: if ! test -d csdn-passport
:: then
 	mkdir csdn-passport
:: fi
cd csdn-passport
java -classpath "%cp%" org.apache.axis.wsdl.WSDL2Java %wsdlUrl%
javac -classpath "%cp%" -Xlint:unchecked net/csdn/passport/*.java
jar cvf csdn-passport-1.0.jar net/csdn/passport/*.class
cd ../../src/main/assembly/