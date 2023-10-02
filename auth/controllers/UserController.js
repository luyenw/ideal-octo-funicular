const User = require("../schemas/User")

const controller = {
    get_id: async (req, res)=>{
        const id = req.params.id
        const found_user = await User.findOne({id: id})
        if(!found_user) return res.sendStatus(404)
        const {name, picture, ...rest} = found_user
        return res.status(200).json({name: name, picture: picture})
    }
}
module.exports = controller