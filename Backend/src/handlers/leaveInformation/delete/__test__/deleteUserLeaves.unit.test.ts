/* eslint-disable no-console */
import { handler as deleteUserLeaves } from '../deleteUserLeaves'
import { LambdaResponse } from '../../../utility/responses'

console.log = jest.fn
console.error = jest.fn

jest.mock('../../../common/mainTable', () => ({
    deleteUserLeaveInformation: jest.fn().mockResolvedValueOnce(
    {result:true, message:'mockUserId leave on mockDate had successfully cancel'}),
}))

let matchResponse: LambdaResponse

beforeEach(() => {
    jest.clearAllMocks()
  })

describe('/delete/deleteUserLeaves deleteUserLeaves.ts test', () => {
    test('Delete User Leave successfully', async () => {
        matchResponse = { statusCode: 200, body: JSON.stringify({
          result:true, message: 'mockUserId leave on mockDate had successfully cancel'})}
        const response = await deleteUserLeaves(
            {body : JSON.stringify({
                userId : 'mockUserId',
                date: 'mockDate'
            })}
        )
        expect(response.statusCode).toStrictEqual(matchResponse.statusCode)
        expect(response.body).toStrictEqual(matchResponse.body)
    })

  test('Delete User Leave unsuccessfully, event data as undefined', async () => {
        matchResponse = { statusCode: 500, body: ' ' }
        const response = await deleteUserLeaves(undefined)
        expect(response.statusCode).toStrictEqual(matchResponse.statusCode)
    })
})