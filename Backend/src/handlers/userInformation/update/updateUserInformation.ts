/* eslint-disable no-console */
import { putUserInformation } from '../../common/mainTable'
import { LambdaResponse } from '../../utility/responses'
import { log2CloudWatch , error2CloudWatch } from '../../utility/cloudWatch'

/**
 * Update User Information
 * @param event - User Id
 * @returns APIGatewayProxyResult
 */
async function updateUserInformation(event): Promise<LambdaResponse> {

  // Promise return params
  const apiResponse: LambdaResponse =
  {
    statusCode: 200,
    body: '',
  }
  try {
    event = JSON.parse(event.body)
    const result = await putUserInformation(event.userId, event.name, event.dateofbirth, event.phonenumber, event.email)
    apiResponse.body = result.message
    log2CloudWatch('updateUserInformation.ts','updateUserInformation','User information successfully Updated')
  }
  catch (err) {
    apiResponse.statusCode = 500
    apiResponse.body = err
    error2CloudWatch('updateUserInformation.ts','updateUserInformation',err)
  }

  return apiResponse
}
export const handler = updateUserInformation
