/* eslint-disable no-console */
import { queryUserLeaveInformation } from '../../common/mainTable'
import { LambdaResponse } from '../../utility/responses'
import { log2CloudWatch , error2CloudWatch } from '../../utility/cloudWatch'

/**
 * Retrieve User Leaves
 * @param event - User Id
 * @returns APIGatewayProxyResult
 */
async function retrieveUserLeaves(event): Promise<LambdaResponse> {

  // Promise return params
  const apiResponse: LambdaResponse =
  {
    statusCode: 200,
    body: '',
  }
  try {
    const { userId } = event.queryStringParameters
    const result = await queryUserLeaveInformation(
      userId
      )
    apiResponse.body = JSON.stringify(result)
    log2CloudWatch('retrieveUserLeaves.ts','retrieveUserLeaves','User leave information successfully retrieve')
  }
  catch (err) {
    apiResponse.statusCode = 500
    apiResponse.body = err
    error2CloudWatch('retrieveUserLeaves.ts','retrieveUserLeaves',err)
  }

  return apiResponse
}
export const handler = retrieveUserLeaves
