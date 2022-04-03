/* eslint-disable no-console */
import { putUserPassword } from '../../common/mainTable'
import { LambdaResponse } from '../../utility/responses'
import { log2CloudWatch , error2CloudWatch } from '../../utility/cloudWatch'

/**
 * Update Password
 * @param event - User Id
 * @returns APIGatewayProxyResult
 */
async function updatePassword(event): Promise<LambdaResponse> {

  // Promise return params
  const apiResponse: LambdaResponse =
  {
    statusCode: 200,
    body: '',
  }
  try {
    event = JSON.parse(event.body)
    const result = await putUserPassword(event.UserId, event.newPassword)
    apiResponse.body = JSON.stringify(result)
    log2CloudWatch('updatePassword.ts','updatePassword','User password successfully update')
  }
  catch (err) {
    apiResponse.statusCode = 500
    apiResponse.body = JSON.stringify({result:false, message:'Error Updating Password'})
    error2CloudWatch('updatePassword.ts','updatePassword',err)
  }
  return apiResponse
}
export const handler = updatePassword
