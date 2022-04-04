/* eslint-disable no-console */
import { handler as retrievePassword } from '../retrievePassword'
import { LambdaResponse } from '../../../utility/responses'

console.log = jest.fn
console.error = jest.fn

jest.mock('../../../common/mainTable', () => ({
    queryPassword:jest.fn().mockResolvedValueOnce([
        {
            PK:{S:'mockPK'},
            SK:{S:'PASSWORD#mockPassword'},
            UserId:{S:'mockUserId'}
        }]).mockResolvedValueOnce([{
                PK:{S:'mockPK'},
                SK:{S: undefined},
                UserId:{S:'mockUserId'}
            }
        ])
}))

let matchResponse: LambdaResponse

beforeEach(() => {
    jest.clearAllMocks()
  })

describe('/retrieve/retrievePassword retrievePassword.ts test', () => {
    test('Retrieve current User Password based on UserId', async () => {
        matchResponse = { statusCode: 200, body: JSON.stringify({password:'mockPassword'})}
        const response = await retrievePassword(
            {queryStringParameters: {
                userId: 'mockUserId',
            }}
        )
        expect(response.statusCode).toStrictEqual(matchResponse.statusCode)
        expect(response.body).toStrictEqual(matchResponse.body)
})

test('Fail to retrieve current user password, due to SK undefined', async () => {
    matchResponse = { statusCode: 500, body: ' ' }
    const response = await retrievePassword(
        {queryStringParameters: {
            userId: 'mockUserId',
        }}
    )
    expect(response.statusCode).toStrictEqual(matchResponse.statusCode)
})

  test('Fail to retrieve current user password, event data as undefined', async () => {
        matchResponse = { statusCode: 500, body: ' ' }
        const response = await retrievePassword(undefined)
        expect(response.statusCode).toStrictEqual(matchResponse.statusCode)
    })
})