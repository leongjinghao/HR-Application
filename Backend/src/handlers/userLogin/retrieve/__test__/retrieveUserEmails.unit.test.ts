/* eslint-disable no-console */
import { handler as retrieveUserEmails } from '../retrieveUserEmails'
import { LambdaResponse } from '../../../utility/responses'

console.log = jest.fn
console.error = jest.fn

jest.mock('../../../common/mainTable', () => ({
    queryUserEmail:jest.fn().mockResolvedValueOnce(
        {result:true, message:'User Email Found'})
}))

let matchResponse: LambdaResponse

beforeEach(() => {
    jest.clearAllMocks()
  })

describe('/retrieve/retrieveUserEmails retrieveUserEmails.ts test', () => {
    test('Check if user email exists based on User Email', async () => {
        matchResponse = { statusCode: 200, body: JSON.stringify({
            result:true,message:'User Email Found'})}
            const response = await retrieveUserEmails(
            {queryStringParameters: {
                userEmail: 'mockEmail',
            }}
        )
        expect(response.statusCode).toStrictEqual(matchResponse.statusCode)
        expect(response.body).toStrictEqual(matchResponse.body)
})

  test('Fail to check if user email exists, event data as undefined', async () => {
        matchResponse = { statusCode: 500, body: ' ' }
        const response = await retrieveUserEmails(undefined)
        expect(response.statusCode).toStrictEqual(matchResponse.statusCode)
    })
})