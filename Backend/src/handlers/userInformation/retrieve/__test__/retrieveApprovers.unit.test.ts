/* eslint-disable no-console */
import { handler as retrieveApprovers } from '../retrieveApprovers'
import { LambdaResponse } from '../../../utility/responses'
import { mockResultApprover } from './mockData'

console.log = jest.fn
console.error = jest.fn

jest.mock('../../../common/mainTable', () => ({
  queryUserDepartment: jest.fn().mockResolvedValueOnce('mockDepartment'),
  queryUserInformationByDepartment:jest.fn().mockResolvedValueOnce([{
      PK: {S:'USER#mockManagerPK'},
      SK: {S:'mockSK'},
      Name: {S:'mockName'},
      DOB: {S:'mockDOB'},
      Mobile: {S:'mockMobile'},
      Email: {S:'mockEmail'},
      Department: {S:'mockDepartment'},
      Picture: {S:'mockPicture'},
      Al: {S:'mockAL'},
      MC: {S:'mockMC'},
      OIL: {S:'mockOIL'},
      Role: {S:'Manager'},
  },
  {
    PK: {S:'USER#mockUserId'},
    SK: {S:'mockSK'},
    Name: {S:'mockName'},
    DOB: {S:'mockDOB'},
    Mobile: {S:'mockMobile'},
    Email: {S:'mockEmail'},
    Department: {S:'mockDepartment'},
    Picture: {S:'mockPicture'},
    Al: {S:'mockAL'},
    MC: {S:'mockMC'},
    OIL: {S:'mockOIL'},
    Role: {S:'Developer'},
},
])
}))

let matchResponse: LambdaResponse

beforeEach(() => {
    jest.clearAllMocks()
  })

describe('/retrieve/retrieveApprovers retrieveApprovers.ts test', () => {
    test('Retrieve Approvers based on the Department', async () => {
        matchResponse = { statusCode: 200, body: JSON.stringify(mockResultApprover)}
        const response = await retrieveApprovers(
            {queryStringParameters: {
                userId: 'mockUserId',
            }}
        )
        expect(response.statusCode).toStrictEqual(matchResponse.statusCode)
        expect(response.body).toStrictEqual(matchResponse.body)
})

  test('Fail to Retrieve Approvers, event data as undefined', async () => {
        matchResponse = { statusCode: 500, body: ' ' }
        const response = await retrieveApprovers(undefined)
        expect(response.statusCode).toStrictEqual(matchResponse.statusCode)
    })
})