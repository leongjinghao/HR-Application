/* eslint-disable no-console */
import { handler as retrieveUserInformation } from '../retrieveUserInformation'
import { LambdaResponse } from '../../../utility/responses'
import { mockResultUserInformation } from './mockData'

console.log = jest.fn
console.error = jest.fn

jest.mock('../../../common/mainTable', () => ({
  queryUserInformation:jest.fn().mockResolvedValueOnce({Items:[{
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
]})
}))

let matchResponse: LambdaResponse

beforeEach(() => {
    jest.clearAllMocks()
  })

describe('/retrieve/retrieveUserInformation retrieveUserInformation.ts test', () => {
    test('Retrieve User Information based on User Id', async () => {
        matchResponse = { statusCode: 200, body: JSON.stringify(mockResultUserInformation)}
        const response = await retrieveUserInformation(
            {queryStringParameters: {
                userId: 'mockUserId',
            }}
        )
        expect(response.statusCode).toStrictEqual(matchResponse.statusCode)
        expect(response.body).toStrictEqual(matchResponse.body)
})

  test('Fail to Retrieve user information, event data as undefined', async () => {
        matchResponse = { statusCode: 500, body: ' ' }
        const response = await retrieveUserInformation(undefined)
        expect(response.statusCode).toStrictEqual(matchResponse.statusCode)
    })
})