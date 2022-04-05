/* eslint-disable no-console */
import { handler as sesSendEmail } from '../sesSendEmail'
import { LambdaResponse } from '../responses'
import AWS from 'aws-sdk'

console.log = jest.fn
console.error = jest.fn

let matchResponse: LambdaResponse

jest.mock('aws-sdk', () => {
    const sesSendEmailPromiseMock = jest.fn()
    .mockResolvedValue(true)
    return {
      SES: jest.fn(() => ({
        sendEmail: () => ({
          promise: sesSendEmailPromiseMock
        }),
      }),
      )}
  })

beforeEach(() => {
    jest.clearAllMocks()
  })

const ses = new AWS.SES({})

describe('/utility/sesSendEmail sesSendEmail.ts test', () => {
    test('Send email successfully', async () => {
        (ses.sendEmail().promise as jest.Mock).mockResolvedValue(true)
        matchResponse = { statusCode: 200, body: 'Email Sent Successfully' }
        const response = await sesSendEmail(
            {body : JSON.stringify({
                recipientEmail : 'test@gmail.com',
            })}
        )
        expect(response.body).toStrictEqual(JSON.stringify({result:true,message:'Email Sent Successfully'}))
    })

  test('Fail to send email, event data as undefined', async () => {
        matchResponse = { statusCode: 500, body: ' ' }
        const response = await sesSendEmail(undefined)
        expect(response.statusCode).toStrictEqual(matchResponse.statusCode)
    })
})