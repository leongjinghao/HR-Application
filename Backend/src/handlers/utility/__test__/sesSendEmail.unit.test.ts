/* eslint-disable no-console */
import { handler as sesSendEmail } from '../sesSendEmail'
import { LambdaResponse } from '../responses'

console.log = jest.fn
console.error = jest.fn

let matchResponse: LambdaResponse

beforeEach(() => {
    jest.clearAllMocks()
  })

describe('/utility/sesSendEmail sesSendEmail.ts test', () => {
    test('Create User Leave successfully', async () => {
        matchResponse = { statusCode: 200, body: 'Email had been send successfully' }
        const response = await sesSendEmail(
            {body : JSON.stringify({
                recipientEmail : 'test@gmail.com',
            })}
        )
        expect(response.statusCode).toStrictEqual(matchResponse.statusCode)
        expect(response.body).toStrictEqual(matchResponse.body)
    })

  test('Create User Leave unsuccessfully, event data as undefined', async () => {
        matchResponse = { statusCode: 500, body: ' ' }
        const response = await sesSendEmail(undefined)
        expect(response.statusCode).toStrictEqual(matchResponse.statusCode)
    })
})