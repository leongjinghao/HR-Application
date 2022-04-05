/* eslint-disable no-console */
import { handler as retrieveUserSchedule } from '../retrieveUserSchedule'
import { LambdaResponse } from '../../../utility/responses'

console.log = jest.fn
console.error = jest.fn

jest.mock('../../../common/mainTable', () => ({
    queryUserSchedule:jest.fn().mockResolvedValueOnce([{
      PK: {S:'mockPK'},
      SK: {S:'mockSK'},
      Schedule: {S:'mockSchedule'},
      WorkLocation: {S:'mockWorkLocation'},
  }])
}))

let matchResponse: LambdaResponse

beforeEach(() => {
    jest.clearAllMocks()
  })

describe('/retrieve/retrieveUserSchedule retrieveUserSchedule.ts test', () => {
    test('Retrieve User Information based on User Id', async () => {
        matchResponse = { statusCode: 200, body: JSON.stringify(
            {UserId: 'mockPK', Date: 'mockSK', Schedule: 'mockSchedule', WorkLocation: 'mockWorkLocation'})}
        const response = await retrieveUserSchedule(
            {queryStringParameters: {
                userId: 'mockUserId',
                date: 'mockDate'
            }}
        )
        expect(response.statusCode).toStrictEqual(matchResponse.statusCode)
        expect(response.body).toStrictEqual(matchResponse.body)
})

  test('Fail to Retrieve user information, event data as undefined', async () => {
        matchResponse = { statusCode: 500, body: ' ' }
        const response = await retrieveUserSchedule(undefined)
        expect(response.statusCode).toStrictEqual(matchResponse.statusCode)
    })
})