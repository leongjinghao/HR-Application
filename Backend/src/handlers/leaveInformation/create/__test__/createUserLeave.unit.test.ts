/* eslint-disable no-console */
import { handler as createUserLeave } from '../createUserLeave'
import { LambdaResponse } from '../../../utility/responses'

console.log = jest.fn
console.error = jest.fn

jest.mock('../../../common/mainTable', () => ({
    putCreateLeave: jest.fn().mockResolvedValueOnce(
    {result:200, message:'User leave successfully created.'}),
}))

let matchResponse: LambdaResponse

beforeEach(() => {
    jest.clearAllMocks()
  })

describe('/create/createUserLeave createUserLeave.ts test', () => {
    test('Create User Leave successfully', async () => {
        matchResponse = { statusCode: 200, body: 'User leave successfully created.' }
        const response = await createUserLeave(
            {body : JSON.stringify({
                userId : 'abc',
                startEndDate:'12122222/1232122',
                leaveType:'MC',
                approver:'Ali',
                remarks:'nil',
                status:'Pending'
            })}
        )
        expect(response.statusCode).toStrictEqual(matchResponse.statusCode)
        expect(response.body).toStrictEqual(matchResponse.body)
    })

  test('Create User Leave unsuccessfully, event data as undefined', async () => {
        matchResponse = { statusCode: 500, body: ' ' }
        const response = await createUserLeave(undefined)
        expect(response.statusCode).toStrictEqual(matchResponse.statusCode)
    })
})