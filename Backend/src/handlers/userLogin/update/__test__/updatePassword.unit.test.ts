/* eslint-disable no-console */
import { handler as updatePassword } from '../updatePassword'
import { LambdaResponse } from '../../../utility/responses'

console.log = jest.fn
console.error = jest.fn

jest.mock('../../../common/mainTable', () => ({
    queryPassword:jest.fn().mockResolvedValueOnce([
        {
            PK:{S:'mockPK'},
            SK:{S:'mockSK'},
            UserId:{S:'mockUserId'}
        }]
    ),
    putUserPassword: jest.fn().mockResolvedValueOnce(
    {result:true, message:'Updated user password.'}),
}))

let matchResponse: LambdaResponse

beforeEach(() => {
    jest.clearAllMocks()
  })

describe('/update/updatePassword updatePassword.ts test', () => {
    test('Update user successfully', async () => {
        matchResponse = { statusCode: 200, body: JSON.stringify({
            result:true,message:'Updated user password.'})}
        const response = await updatePassword(
            {body : JSON.stringify({
                UserId : 'mockUserId',
                newPassword:'mockNewPassword',
            })}
        )
        expect(response.statusCode).toStrictEqual(matchResponse.statusCode)
        expect(response.body).toStrictEqual(matchResponse.body)
    })

  test('Update User Information unsuccessfully, event data as undefined', async () => {
        matchResponse = { statusCode: 500, body: ' ' }
        const response = await updatePassword(undefined)
        expect(response.statusCode).toStrictEqual(matchResponse.statusCode)
    })
})