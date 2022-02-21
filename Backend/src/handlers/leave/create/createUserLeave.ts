import { createLeave } from '../../common/mainTable';
import { LambdaResponse } from '../../common/responses';
import { log2CloudWatch , error2CloudWatch } from '../../common/cloudWatch'

/**
 * Create User Leave
 * @param event - User Leave Details
 * @returns APIGatewayProxyResult
 */
async function createUserLeave(event): Promise<LambdaResponse> {
  event = JSON.parse(event.body);

  // Promise return params
  const apiResponse: LambdaResponse =
  {
    statusCode: 200,
    body: '',
  }

  try {
    const result = await createLeave(event.userId,event.startEndDate,event.leaveType,event.approver,event.remarks,event.status)
    apiResponse.body = result.message
  }
  catch(err)
  {
    apiResponse.body = err
  }
  
  return apiResponse;
}
export const handler = createUserLeave;
