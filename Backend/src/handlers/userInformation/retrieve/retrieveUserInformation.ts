/* eslint-disable no-console */
import { queryUserInformation } from '../../common/mainTable'
import { LambdaResponse } from '../../utility/responses'
import { log2CloudWatch , error2CloudWatch } from '../../utility/cloudWatch'

/**
 * Retrieve User Information
 * @param event - User Id
 * @returns APIGatewayProxyResult
 */
async function retrieveUserInformation(event): Promise<LambdaResponse> {

  // Promise return params
  const apiResponse: LambdaResponse =
  {
    statusCode: 200,
    body: '',
  }
  try {
    const { userId } = event.queryStringParameters
    const result = await queryUserInformation(userId)
    apiResponse.body = JSON.stringify(result)
    log2CloudWatch('retrieveUserInformation.ts','retrieveUserInformation','User information successfully retrieve')
  }
  catch (err) {
    apiResponse.statusCode = 500
    apiResponse.body = err
    error2CloudWatch('retrieveUserInformation.ts','retrieveUserInformation',err)
  }

  return apiResponse
}
export const handler = retrieveUserInformation
