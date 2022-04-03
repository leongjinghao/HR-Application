/* eslint-disable no-console */
import { queryUserEmail } from '../../common/mainTable'
import { LambdaResponse } from '../../utility/responses'
import { log2CloudWatch , error2CloudWatch } from '../../utility/cloudWatch'

/**
 * Retrieve User Information
 * @param email - User Email Input
 * @returns APIGatewayProxyResult
 */
async function retrieveUserEmails(email): Promise<LambdaResponse> {

  // Promise return params
  const apiResponse: LambdaResponse =
  {
    statusCode: 200,
    body: '',
  }
  try {
    const { userEmail } = email.queryStringParameters
    const result = await queryUserEmail(userEmail)
    console.log(result)
    apiResponse.body = JSON.stringify(result)
    log2CloudWatch('retrieveUserEmails.ts','retrieveUserEmails','User Email Received Successfully')
  }
  catch (err) {
    apiResponse.statusCode = 500
    apiResponse.body = err
    error2CloudWatch('retrieveUserEmails.ts','retrieveUserEmails',err)
  }

  return apiResponse
}
export const handler = retrieveUserEmails
