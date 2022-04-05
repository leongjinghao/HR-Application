/* eslint-disable no-console */
import { handler as authenticateUserLogin } from '../authenticateUserLogin'
import { LambdaResponse } from '../../../utility/responses'

console.log = jest.fn
console.error = jest.fn

jest.mock('../../../common/mainTable', () => ({
    queryUserLogin:jest.fn().mockResolvedValueOnce([
        {
            PK:{S:'mockPK'},
            SK:{S:'PASSWORD#mockPassword'},
            UserId:{S:'mockUserId'}
        }])
}))

let matchResponse: LambdaResponse

beforeEach(() => {
    jest.clearAllMocks()
  })

describe('/retrieve/authenticateUserLogin authenticateUserLogin.ts test', () => {
    test('Authentication Login, Successful', async () => {
        matchResponse = { statusCode: 200, body: JSON.stringify({UserId:'mockUserId',Result:true})}
        const response = await authenticateUserLogin(
            {queryStringParameters: {
                userEmail: 'mockUserId',
                userPassword: 'mockPassword'
            }}
        )
        expect(response.statusCode).toStrictEqual(matchResponse.statusCode)
        expect(response.body).toStrictEqual(matchResponse.body)
})

  test('Fail to authenticate, event data as undefined', async () => {
        matchResponse = { statusCode: 500, body: ' ' }
        const response = await authenticateUserLogin(undefined)
        expect(response.statusCode).toStrictEqual(matchResponse.statusCode)
    })
})