const oauth = require("../oauth/index");
const axios = require("axios");
const User = require("../schemas/User");
const controller = {
  verify: async (req, res) => {
    const access_token = req.cookies.access_token;
    if (!access_token) return res.status(401).send("denied");
    const query = `https://www.googleapis.com/oauth2/v1/userinfo?alt=json&access_token=${access_token}`;
    console.log(query);
    try {
      response = await axios.get(query, {
        withCredentials: true,
        credentials: "include",
      });
      const data = await response.data;
      if (data.code == 401) return res.status(401).send("denied");
      return res.status(200).send(data);
    } catch (err) {
      console.log(err.message);
      return res.status(401).send("denied");
    }
  },
  login: async (req, res) => {
    try {
      res.redirect(oauth.request_get_auth_code_url);
    } catch (error) {
      res.sendStatus(500);
      console.log(error.message);
    }
  },
  oauth_callback: async (req, res) => {
    const authorization_token = req.query.code;
    try {
      // ! get access token using authorization token
      var response = await oauth.get_access_token(authorization_token);
      // get access token from payload
      const { access_token } = await response.data;
      console.log(access_token);
      res.cookie("access_token", access_token);
      // save or update user 
      const query = `https://www.googleapis.com/oauth2/v1/userinfo?alt=json&access_token=${access_token}`;
      try {
        response = await axios.get(query, {
          withCredentials: true,
          credentials: "include",
        });
        const data = await response.data;
        if (data.code == 401) return res.status(401).send("denied");
        console.log(data.id)
        const found_user = await User.findOne({id: data.id})
        if(found_user){
          await User.updateOne({where: {id: data.id}}, data)
        }else
          await new User(data).save()
        return res.redirect("http://localhost:8082");
      } catch (err) {
        console.log(err.message);
        return res.status(401).send("denied");
      }
    } catch (error) {
      console.log(error.message);
      return res.sendStatus(500);
    }
  },
};
module.exports = controller;
