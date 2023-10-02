const express = require('express')
const app = express()
require("dotenv").config();
const cookieParser = require('cookie-parser')

const cors = require("cors");
const connect = require('./config/db/index.js')
connect()
//
const allowedOrigins = [
  "localhost",
  "http://localhost:3001",
  "http://127.0.0.1:8080",
  "http://localhost:8082/",
];
var corsOptions = {
  origin: function (origin, callback) {
    if (allowedOrigins.indexOf(origin) !== -1) {
      callback(null, true);
    } else {
      // callback(new Error('Not allowed by CORS'))
      // console.log("------")
      // console.log("origin",origin)
      callback(null, true);
    }
  },
  credentials: true,
};
const rootRouter = require('./routes/RootRoute.js')
const userRouter = require('./routes/UserRoute.js')
//
app.use(cors(corsOptions))
app.use(cookieParser());
app.use('/', rootRouter)
app.use('/u', userRouter)
//
app.listen(3001, ()=>{
    console.log('running on port 3001..')
})