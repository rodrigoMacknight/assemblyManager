# assembly manager spring boot

## steps
- gradle build
- connect to ec2: 
- ssh -i "assembly.pem" ec2-user@ec2-3-16-161-33.us-east-2.compute.amazonaws.com
- scp -i ~/Downloads/assembly.pem build/libs/assemblyManager-0.0.1-SNAPSHOT.jar ec2-user@ec2-3-16-161-33.us-east-2.compute.amazonaws.com:~
- access: http://ec2-3-16-161-33.us-east-2.compute.amazonaws.com:8080/
- import assembly.postman_coolection and have fun


