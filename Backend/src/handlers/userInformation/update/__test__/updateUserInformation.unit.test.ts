/* eslint-disable no-console */
import { handler as updateUserInformation } from '../updateUserInformation'
import { LambdaResponse } from '../../../utility/responses'

console.log = jest.fn
console.error = jest.fn

jest.mock('../../../common/mainTable', () => ({
    putUserInformation: jest.fn().mockResolvedValueOnce(
    {result:true, message:'Updated user Information.'}),
}))

let matchResponse: LambdaResponse

beforeEach(() => {
    jest.clearAllMocks()
  })

describe('/update/updateUserInformation updateUserInformation.ts test', () => {
    test('Update user successfully', async () => {
        matchResponse = { statusCode: 200, body: JSON.stringify({
            result:true,message:'Updated user Information.'})}
        const response = await updateUserInformation(
            {body : JSON.stringify({
                userId : 'mockUserId',
                name:'mockUserId',
                department:'mockDepartment',
                dateofbirth:'mockDateOfBirth',
                phonenumber:'mockPhoneNumber',
                email:'mockEmail'
            })}
        )
        expect(response.statusCode).toStrictEqual(matchResponse.statusCode)
        expect(response.body).toStrictEqual(matchResponse.body)
    })

  test('Update User Information unsuccessfully, event data as undefined', async () => {
        matchResponse = { statusCode: 500, body: ' ' }
        const response = await updateUserInformation(undefined)
        expect(response.statusCode).toStrictEqual(matchResponse.statusCode)
    })
})