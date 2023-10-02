const mongoose = require('mongoose')

const Schema = mongoose.Schema
const UserSchema = new Schema({
    id: String,
    name: String,
    given_name: String, 
    family_name: String,
    picture: String,
    locale: String
}, {
    timestamps: true
})

const User = mongoose.model('user', UserSchema, 'users')
module.exports = User