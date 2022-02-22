import { createLeave } from '../../common/mainTable';
import { LambdaResponse } from '../../utility/responses';
import { log2CloudWatch , error2CloudWatch } from '../../utility/cloudWatch'

/**
 * Create User Leave
 * @param event - User Leave Details
 * @returns APIGatewayProxyResult
 */
async function createUserLeave(event): Promise<LambdaResponse> {

  // Promise return params
  const apiResponse: LambdaResponse =
  {
    statusCode: 200,
    body: '',
  }

  try {
    event = JSON.parse(event.body);
    const result = await createLeave(
      event.userId,
      event.startEndDate,
      event.leaveType,
      event.approver,
      event.remarks,
      event.status
      )
    apiResponse.body = result.message
    log2CloudWatch("createUserLeave.ts","createUserLeave",result.message)
  }
  catch (err) {
    apiResponse.statusCode = 500;
    apiResponse.body = err
    error2CloudWatch("createUserLeave.ts","createUserLeave",err)
  }
  
  return apiResponse;
}
export const handler = createUserLeave;
