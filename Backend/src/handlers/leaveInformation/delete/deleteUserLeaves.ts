/* eslint-disable no-console */
import { deleteUserLeaveInformation } from '../../common/mainTable'
import { LambdaResponse } from '../../utility/responses'
import { log2CloudWatch , error2CloudWatch } from '../../utility/cloudWatch'

/**
 * Delete User Leaves
 * @param event - User Id, Date of Leave
 * @returns APIGatewayProxyResult
 */
async function deleteUserLeaves(event): Promise<LambdaResponse> {

  // Promise return params
  const apiResponse: LambdaResponse =
  {
    statusCode: 200,
    body: '',
  }
  try {
    event = JSON.parse(event.body)
    const result = await deleteUserLeaveInformation(event.userId,event.date)
    apiResponse.body = JSON.stringify(result)
    log2CloudWatch('deleteUserLeaves.ts','deleteUserLeaves','User leaves successfully deleted')
  }
  catch (err) {
    apiResponse.statusCode = 500
    apiResponse.body = err
    error2CloudWatch('deleteUserLeaves.ts','deleteUserLeaves',err)
  }

  return apiResponse
}
export const handler = deleteUserLeaves
