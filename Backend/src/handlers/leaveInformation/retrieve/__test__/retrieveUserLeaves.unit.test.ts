/* eslint-disable no-console */
import { handler as retrieveUserLeaves } from '../retrieveUserLeaves'
import { LambdaResponse } from '../../../utility/responses'
import { mockResultDisplay,mockResultCalendar } from './mockData'

console.log = jest.fn
console.error = jest.fn

jest.mock('../../../common/mainTable', () => ({
    queryUserLeaveInformation: jest.fn().mockResolvedValue(
            [{
              LeaveType: {S:'mockLeaveType'},
              Approver: {S:'mockApprover'},
              Remarks: {S:'mockRemarks'},
              SK: {S:'mockSK'},
              LeaveStatus: {S:'Removed'},
              PK: {S:'mockPK'}
            },
            {
              LeaveType: {S:'mockLeaveType'},
              Approver: {S:'mockApprover'},
              Remarks: {S:'mockRemarks'},
              SK: {S:'mockSK'},
              LeaveStatus: {S:'Approved'},
              PK: {S:'mockPK'}
            }]
        ),
}))

let matchResponse: LambdaResponse

beforeEach(() => {
    jest.clearAllMocks()
  })

describe('/retrieve/retrieveUserLeaves retrieveUserLeaves.ts test', () => {
    test('Retrieve User Leaves successfully based on condition Display', async () => {
        matchResponse = { statusCode: 200, body: JSON.stringify(mockResultDisplay)}
        const response = await retrieveUserLeaves(
            {queryStringParameters: {
                userId: 'mockUserId',
                condition: 'Display'
            }}
        )
        expect(response.statusCode).toStrictEqual(matchResponse.statusCode)
        expect(response.body).toStrictEqual(matchResponse.body)
})

test('Retrieve User Leaves successfully based on condition Calendar', async () => {
  matchResponse = { statusCode: 200, body: JSON.stringify(mockResultCalendar)}
  const response = await retrieveUserLeaves(
      {queryStringParameters: {
          userId: 'mockUserId',
          condition: 'Calendar'
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