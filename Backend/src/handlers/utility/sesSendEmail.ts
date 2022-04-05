import AWS from 'aws-sdk'
import { LambdaResponse } from './responses'
import { log2CloudWatch , error2CloudWatch } from './cloudWatch'

/**
 * Send an email using AWS Simple Email Service
 * @param event Email address of Recipient
 * @returns APIGatewayProxyResult
 */
async function sesSendEmail(event) : Promise<LambdaResponse> {
  // Promise return params
  const ses = new AWS.SES({ region: 'ap-southeast-1' })

  const apiResponse: LambdaResponse =
  {
    statusCode: 200,
    body: '',
  }

  try {
    event = JSON.parse(event.body)
    const params = {
        Destination: {
          ToAddresses: [event.userEmail],
        },
        Message: {
          Body: {
            Text: { Data: 'Hello,\nPlease click on the link below to reset your BlueFlush password.' },
          },
          Subject: { Data: 'Password Recovery' },
        },
        Source: 'blueflushmad@gmail.com',
      }
      await ses.sendEmail(params).promise()
      apiResponse.body = JSON.stringify({result:true,message:'Email Sent Successfully'})
      log2CloudWatch('sesSendEmail.ts','sesSendEmail',apiResponse.body)
  }
  catch (err) {
    apiResponse.statusCode = 500
    apiResponse.body = JSON.stringify({result:false,message:'Email Not Found'})
    error2CloudWatch('sesSendEmail.ts','sesSendEmail',err)
  }

  return apiResponse
}
export const handler = sesSendEmail