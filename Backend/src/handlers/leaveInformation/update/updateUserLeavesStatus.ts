/* eslint-disable no-console */
import { updateLeaveStatus } from '../../common/mainTable'
import { LambdaResponse } from '../../utility/responses'
import { log2CloudWatch , error2CloudWatch } from '../../utility/cloudWatch'

/**
 * Update User Leaves Status
 * @param event - User Id, Date of Leave, Status
 * @returns APIGatewayProxyResult
 */
async function updateUserLeavesStatus(event): Promise<LambdaResponse> {

  // Promise return params
  const apiResponse: LambdaResponse =
  {
    statusCode: 200,
    body: '',
  }
  try {
    event = JSON.parse(event.body)
    const result = await updateLeaveStatus(event.userId,event.date,event.status)
    apiResponse.body = JSON.stringify(result)
    log2CloudWatch('updateUserLeavesStatus.ts','updateUserLeavesStatus','User leave status had been update')
  }
  catch (err) {
    apiResponse.statusCode = 500
    apiResponse.body = err
    error2CloudWatch('updateUserLeavesStatus.ts','updateUserLeavesStatus',err)
  }

  return apiResponse
}
export const handler = updateUserLeavesStatus
