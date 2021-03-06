/* eslint-disable no-console */
import { queryUserLeaveInformation } from '../../common/mainTable'
import { LambdaResponse } from '../../utility/responses'
import { log2CloudWatch, error2CloudWatch } from '../../utility/cloudWatch'

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
    const { userId, condition } = event.queryStringParameters
    const result = await queryUserLeaveInformation(userId)
    const returnResponse = { Items: [{}] }
    if (result !== false && result !== undefined) {
      let count = 0
      let ItemsCount = 0
      while (count < result.length) {
        if (condition === 'Display') {
          if (result[count].LeaveStatus.S !== 'Removed') {
            returnResponse.Items[ItemsCount] = result[count]
            ItemsCount++
          }
        } else if (condition === 'Calendar')
          {
            if (result[count].LeaveStatus.S === 'Approved' || result[count].LeaveStatus.S === 'Removed') {
              returnResponse.Items[ItemsCount] = result[count]
              ItemsCount++
            }
          }
        count++
      }
    }
    apiResponse.body = JSON.stringify(returnResponse)
    log2CloudWatch('retrieveUserLeaves.ts', 'retrieveUserLeaves', 'User leave information successfully retrieve')
  }
  catch (err) {
    apiResponse.statusCode = 500
    apiResponse.body = err
    error2CloudWatch('retrieveUserLeaves.ts', 'retrieveUserLeaves', err)
  }

  return apiResponse
}
export const handler = retrieveUserLeaves
