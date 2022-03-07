/* eslint-disable no-undef */
module.exports = {
  preset: 'ts-jest',
  testEnvironment: 'node',
  coverageDirectory: './tests/coverage',
  verbose: true,
  collectCoverage: true,
  coverageReporters: ['html', 'text', 'text-summary'],
}