/* eslint-disable no-console */
import { handler as updateUserLeavesStatus } from '../updateUserLeavesStatus'
import { LambdaResponse } from '../../../utility/responses'

console.log = jest.fn
console.error = jest.fn

jest.mock('../../../common/mainTable', () => ({
    updateLeaveStatus: jest.fn().mockResolvedValueOnce(
    {result:true, message:'Leave status had been updated successfully.'}),
}))

let matchResponse: LambdaResponse

beforeEach(() => {
    jest.clearAllMocks()
  })

describe('/update/updateUserLeaveStatus updateUserLeaveStatus.ts test', () => {
    test('Update User Leave successfully', async () => {
        matchResponse = { statusCode: 200, body: JSON.stringify({
            result:true, message: 'Leave status had been updated successfully.'})}
        const response = await updateUserLeavesStatus(
            {body : JSON.stringify({
                userId : 'mockUserId',
                date:'mockDate',
                status:'mockStatus',
            })}
        )
        expect(response.statusCode).toStrictEqual(matchResponse.statusCode)
        expect(response.body).toStrictEqual(matchResponse.body)
    })

  test('Update User Leave unsuccessfully, event data as undefined', async () => {
        matchResponse = { statusCode: 500, body: ' ' }
        const response = await updateUserLeavesStatus(undefined)
        expect(response.statusCode).toStrictEqual(matchResponse.statusCode)
    })
})