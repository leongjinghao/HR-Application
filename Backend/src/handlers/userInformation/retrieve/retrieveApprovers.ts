/* eslint-disable no-console */
import { queryUserInformationByDepartment, queryUserDepartment } from '../../common/mainTable'
import { LambdaResponse } from '../../utility/responses'
import { log2CloudWatch, error2CloudWatch } from '../../utility/cloudWatch'

/**
 * Retrieve Manager Level User
 * @returns APIGatewayProxyResult
 */
async function retrieveApprovers(event): Promise<LambdaResponse> {

  // Promise return params
  const apiResponse: LambdaResponse =
  {
    statusCode: 200,
    body: '',
  }
  try {
    const { userId } = event.queryStringParameters
    const userDepartment = await queryUserDepartment(userId)
    let result
    const returnResponse = { Items: [{}] }
    if (userDepartment !== false) {
      result = await queryUserInformationByDepartment(userDepartment)
      if (result !== false) {
        let count = 0
        let ItemsCount = 0
        while (count < result.length) {
          if (result[count].Role.S === 'Manager') {
            if (result[count].PK.S !== 'USER#'+userId){
            returnResponse.Items[ItemsCount] = result[count]
            ItemsCount++
            }
          }
          count++
        }
      }
    }
    apiResponse.body = JSON.stringify(returnResponse)
    log2CloudWatch('retrieveApprovers.ts', 'retrieveApprovers', 'Approver successfully retrieve')
  }
  catch (err) {
    apiResponse.statusCode = 500
    apiResponse.body = err
    error2CloudWatch('retrieveApprovers.ts', 'retrieveApprovers', err)
  }

  return apiResponse
}
export const handler = retrieveApprovers
