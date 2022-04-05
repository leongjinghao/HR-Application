export const mockResultApprover = {Items: [
    {
      PK: {S:'USER#mockManagerPK'},
      SK: {S:'mockSK'},
      Name: {S:'mockName'},
      DOB: {S:'mockDOB'},
      Mobile: {S:'mockMobile'},
      Email: {S:'mockEmail'},
      Department: {S:'mockDepartment'},
      Picture: {S:'mockPicture'},
      Al: {S:'mockAL'},
      MC: {S:'mockMC'},
      OIL: {S:'mockOIL'},
      Role: {S:'Manager'},
    },
  ],
}

export const mockResultUserInformation = {Items: [
  {
    PK: {S:'USER#mockUserId'},
    SK: {S:'mockSK'},
    Name: {S:'mockName'},
    DOB: {S:'mockDOB'},
    Mobile: {S:'mockMobile'},
    Email: {S:'mockEmail'},
    Department: {S:'mockDepartment'},
    Picture: {S:'mockPicture'},
    Al: {S:'mockAL'},
    MC: {S:'mockMC'},
    OIL: {S:'mockOIL'},
    Role: {S:'Developer'},
  },
],
}

module.exports = { mockResultApprover,mockResultUserInformation }