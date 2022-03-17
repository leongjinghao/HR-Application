/* eslint-disable no-console */
import { handler as retrieveUserLeaves } from '../retrieveUserLeaves'
import { LambdaResponse } from '../../../utility/responses'
import { mockResult } from './mockData'

console.log = jest.fn
console.error = jest.fn

jest.mock('../../../common/mainTable', () => ({
    queryUserLeaveInformation: jest.fn().mockResolvedValueOnce(
        {Items: [
            {
              LeaveType: [Object],
              Approver: [Object],
              Remarks: [Object],
              SK: [Object],
              Status: [Object],
              PK: [Object]
            },
            {
              LeaveType: [Object],
              Approver: [Object],
              Remarks: [Object],
              SK: [Object],
              Status: [Object],
              PK: [Object]
            }
          ],
          Count: 2,
          ScannedCount: 2
        }),
}))

let matchResponse: LambdaResponse

beforeEach(() => {
    jest.clearAllMocks()
  })

describe('/retrieve/retrieveUserLeaves retrieveUserLeaves.ts test', () => {
    test('Retrieve User Leaves successfully', async () => {
        matchResponse = { statusCode: 200, body: JSON.stringify(mockResult)}
        const response = await retrieveUserLeaves(
            {queryStringParameters: {
                userId: 'ali123',
            }}
        )
        expect(response.statusCode).toStrictEqual(matchResponse.statusCode)
        expect(response.body).toStrictEqual(matchResponse.body)
})

  test('Fail to Retrieve User Leaves, event data as undefined', async () => {
        matchResponse = { statusCode: 500, body: ' ' }
        const response = await retrieveUserLeaves(undefined)
        expect(response.statusCode).toStrictEqual(matchResponse.statusCode)
    })
})