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
          ToAddresses: [event.recipientEmail],
        },
        Message: {
          Body: {
            Text: { Data: 'Test' },
          },
          Subject: { Data: 'Test Email' },
        },
        Source: 'weianngo@gmail.com',
      }
      await ses.sendEmail(params).promise()
      apiResponse.body = 'Email had been send successfully'
      log2CloudWatch('sesSendEmail.ts','sesSendEmail',apiResponse.body)
  }
  catch (err) {
    apiResponse.statusCode = 500
    apiResponse.body = err
    error2CloudWatch('sesSendEmail.ts','sesSendEmail',err)
  }

  return apiResponse
}
export const handler = sesSendEmail