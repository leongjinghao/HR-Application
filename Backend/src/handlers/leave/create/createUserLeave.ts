import { createLeave } from '../../common/mainTable';
import { LambdaResponse } from '../../common/responses';

/**
 * Create User Leave
 * @param event - User Leave Details
 * @returns APIGatewayProxyResult
 */
async function createUserLeave(): Promise<LambdaResponse> {

  // Promise return params
  const apiResponse: LambdaResponse =
  {
    statusCode: 200,
    body: '',
  }

  await createLeave('Halo','12122012/13122021','MC','Ahmad','NIL','Pending') ;
  
  return apiResponse;
}
export const handler = createUserLeave;
