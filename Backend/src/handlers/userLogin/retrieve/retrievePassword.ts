/* eslint-disable no-console */
import { queryPassword } from '../../common/mainTable'
import { LambdaResponse } from '../../utility/responses'
import { log2CloudWatch , error2CloudWatch } from '../../utility/cloudWatch'

/**
 * Retrieve Password
 * @param event - User Id
 * @returns APIGatewayProxyResult
 */
async function retrievePassword(event): Promise<LambdaResponse> {

  // Promise return params
  const apiResponse: LambdaResponse =
  {
    statusCode: 200,
    body: '',
  }
  try {
    const { UserId } = event.queryStringParameters
    const result = await queryPassword(UserId)
    apiResponse.body = JSON.stringify({password:result[0].SK.S!.substring(9)})
    log2CloudWatch('retrievePassword.ts','retrievePassword','User password successfully retrieve')
  }
  catch (err) {
    apiResponse.statusCode = 500
    apiResponse.body = JSON.stringify({password:''})
    error2CloudWatch('retrievePassword.ts','retrievePassword',err)
  }
  return apiResponse
}
export const handler = retrievePassword
