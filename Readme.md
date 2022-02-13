<h1>Frontend</h1>

<h2>Configuration</h2> 

<h1>Backend</h1>

<h2>Configuration</h2> 

<b>Install : https://aws.amazon.com/cli/ <br>
    
using node - version 14.15.4

*If needed to downgrade node version can use nvm from https://github.com/coreybutler/nvm-windows/releases

EG: node nvm install 14.15.4
    node nvm use 14.15.4
    
npm install -g serverless <br>
npm install <br>
aws configure : <br></b>

Example Value to input for aws configure :<br>
AWS Access Key ID [] : Given Access Key <br>
AWS Secret Access Key [] : Given Secret Access Key <br>
Default Region Name : ap-southeast-1 <br>
Default output format : yaml <br>

Duplciate env.sample to .env and rename DEVELOPER it with your name

<h3> Other Commands :</h3>
sls deploy  - to upload the code into AWS, it will create a stack in the AWS Cloudformation <br>
sls info    - to view aws information <br>
sls remove  - remove stack from AWS <br>

<h3>Library used </h3>
    
serverless <br>
Jest (Unit Test) <br>
Eslint (Code Quality Check) <br>

<h3>Other Information </h3>
Access AWS  Cloudwatch/Logs/Log groups/select your lambda function/view the logs <br>
