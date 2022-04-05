/* eslint-disable no-console */
import { handler as sesSendEmail } from '../sesSendEmail'
import { LambdaResponse } from '../responses'
import AWSMock from 'aws-sdk-mock'
import AWS from 'aws-sdk'

console.log = jest.fn
console.error = jest.fn

let matchResponse: LambdaResponse

beforeEach(() => {
    jest.clearAllMocks()
  })

describe('/utility/sesSendEmail sesSendEmail.ts test', () => {
    test('Send email successfully', async () => {
        AWSMock.setSDKInstance(AWS)
        AWSMock.mock('SES',sesSendEmail,)
        matchResponse = { statusCode: 200, body: 'Email Sent Successfully' }
        const response = await sesSendEmail(
            {body : JSON.stringify({
                recipientEmail : 'test@gmail.com',
            })}
        )
        expect(response.statusCode).toStrictEqual(matchResponse.statusCode)
        expect(response.body).toStrictEqual(matchResponse.body)
    })

  test('Fail to send email, event data as undefined', async () => {
        matchResponse = { statusCode: 500, body: ' ' }
        const response = await sesSendEmail(undefined)
        expect(response.statusCode).toStrictEqual(matchResponse.statusCode)
    })
})