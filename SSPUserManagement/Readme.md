db2 import from /tmp/users.del of del insert into users
db2 import from /tmp/roles.del of del insert into roles
db2 import from /tmp/userrole.del of del insert into userrole

https://localhost:1026/swagger-ui.html

curl --location --request POST 'https://localhost:1025/oauth/token' --header 'Authorization: Basic c3NwX2RvbWFpbjpwYXNzd29yZA==' --header 'Content-Type: application/x-www-form-urlencoded' -d 'grant_type=password' -d 'username=system_sshettigar75@gmail' -d 'password=password' -k

curl --location --request GET 'https://localhost:1026/getOAuthDetails' -H 'Authorization: Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2NDkxNTYzODUsInVzZXJfbmFtZSI6InN5c3RlbV9zc2hldHRpZ2FyNzVAZ21haWwiLCJhdXRob3JpdGllcyI6WyJ0ZW5hbnRhZG1pbiIsInN1cGVydXNlciIsInRlbmFudHVzZXIiXSwianRpIjoiM2UyNWI3ZTUtNDI5Yy00ZGU3LWJlOWQtOTcyYTgyNWEwZTJmIiwiY2xpZW50X2lkIjoic3NwX2RvbWFpbiIsInNjb3BlIjpbInJlYWQiLCJ3cml0ZSJdfQ.noDa_3tMi9havihsvcEKvKk73NyYYwKbxoGBgB-9fHBLzgPjyVt999WMDZoaL_zArTeUUKkbc7sSlrWMEHYNc4JYNEgfuLNQks_Y3X55fgz2O9YduYvlwOxOb56OA0I8FcUe-IMrKk3n7ib4nEito3XEnqnktjPls2twC7fwsL9GuqKiSY4jNxCQogB4wLB2rCr8uiqR0jBo3-hCVYHx4m_mqsrqFV4dKsXywkXPOKEb1YXu1Vpe4Leli7gAwbUfe7vecJKZl-XSZ-RQ_57pkg0Y9wbSdKG-Nin_7uSJ_2MSKC6P9yw5JRQ_abl_PL_yyx-chmhkWgEMTzmGTS9BSQ' -k


curl --location --request GET 'https://localhost:1026/sspService/getOAuthDetails' -H 'Authorization: Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2NDkxNTYzODUsInVzZXJfbmFtZSI6InN5c3RlbV9zc2hldHRpZ2FyNzVAZ21haWwiLCJhdXRob3JpdGllcyI6WyJ0ZW5hbnRhZG1pbiIsInN1cGVydXNlciIsInRlbmFudHVzZXIiXSwianRpIjoiM2UyNWI3ZTUtNDI5Yy00ZGU3LWJlOWQtOTcyYTgyNWEwZTJmIiwiY2xpZW50X2lkIjoic3NwX2RvbWFpbiIsInNjb3BlIjpbInJlYWQiLCJ3cml0ZSJdfQ.noDa_3tMi9havihsvcEKvKk73NyYYwKbxoGBgB-9fHBLzgPjyVt999WMDZoaL_zArTeUUKkbc7sSlrWMEHYNc4JYNEgfuLNQks_Y3X55fgz2O9YduYvlwOxOb56OA0I8FcUe-IMrKk3n7ib4nEito3XEnqnktjPls2twC7fwsL9GuqKiSY4jNxCQogB4wLB2rCr8uiqR0jBo3-hCVYHx4m_mqsrqFV4dKsXywkXPOKEb1YXu1Vpe4Leli7gAwbUfe7vecJKZl-XSZ-RQ_57pkg0Y9wbSdKG-Nin_7uSJ_2MSKC6P9yw5JRQ_abl_PL_yyx-chmhkWgEMTzmGTS9BSQ' -k

curl --location --request GET 'https://localhost:1026/sspService/mlService' -H 'Authorization: Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2NDkxNTYzODUsInVzZXJfbmFtZSI6InN5c3RlbV9zc2hldHRpZ2FyNzVAZ21haWwiLCJhdXRob3JpdGllcyI6WyJ0ZW5hbnRhZG1pbiIsInN1cGVydXNlciIsInRlbmFudHVzZXIiXSwianRpIjoiM2UyNWI3ZTUtNDI5Yy00ZGU3LWJlOWQtOTcyYTgyNWEwZTJmIiwiY2xpZW50X2lkIjoic3NwX2RvbWFpbiIsInNjb3BlIjpbInJlYWQiLCJ3cml0ZSJdfQ.noDa_3tMi9havihsvcEKvKk73NyYYwKbxoGBgB-9fHBLzgPjyVt999WMDZoaL_zArTeUUKkbc7sSlrWMEHYNc4JYNEgfuLNQks_Y3X55fgz2O9YduYvlwOxOb56OA0I8FcUe-IMrKk3n7ib4nEito3XEnqnktjPls2twC7fwsL9GuqKiSY4jNxCQogB4wLB2rCr8uiqR0jBo3-hCVYHx4m_mqsrqFV4dKsXywkXPOKEb1YXu1Vpe4Leli7gAwbUfe7vecJKZl-XSZ-RQ_57pkg0Y9wbSdKG-Nin_7uSJ_2MSKC6P9yw5JRQ_abl_PL_yyx-chmhkWgEMTzmGTS9BSQ' -k