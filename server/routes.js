require("./schema");
const Joi = require("joi");
const Mongoose = require("mongoose");

const Part = Mongoose.model("Part");
const Bicycle = Mongoose.model("Bicycle");

module.exports = [
  {
    method: "GET",
    path: "/api/bicycles",
    handler: async (request, h) => {
      try {
        const bicycle = await Bicycle.find().exec();
        return h.response(bicycle);
      } catch (error) {
        console.log(error);
        return h.response(error).code(500);
      }
    },
  },
  {
    method: "GET",
    path: "/api/bicycles/{id}",
    handler: async (request, h) => {
      try {
        const bicycle = await Bicycle.findById(request.params.id).exec();
        if (!bicycle) {
          const error = {
            error: "Not Found",
            message: "Bicycle was not found.",
            statusCode: 404,
          };
          return h.response(error).code(404);
        }
        return h.response(bicycle);
      } catch (error) {
        console.log(error);
        return h.response(error).code(500);
      }
    },
  },
  {
    method: "GET",
    path: "/api/parts",
    handler: async (request, h) => {
      try {
        const part = await Part.find().exec();
        return h.response(part);
      } catch (error) {
        return h.response(error).code(500);
      }
    },
  },
  {
    method: "GET",
    path: "/api/parts/{id}",
    handler: async (request, h) => {
      try {
        const part = await Part.findById(request.params.id).exec();
        if (!part) {
          const error = {
            error: "Not Found",
            message: "Part was not found.",
            statusCode: 404,
          };
          return h.response(error).code(404);
        }
        return h.response(part);
      } catch (error) {
        return h.response(error).code(500);
      }
    },
  },
  {
    method: "PUT",
    path: "/api/parts/{id}",
    options: {
      validate: {
        payload: Joi.object({
          name: Joi.string().optional(),
          imageUrl: Joi.string().optional(),
        }),
        failAction: (request, h, error) => {
          return error.isJoi
            ? h.response(error.details[0]).takeover()
            : h.response(error).takeover();
        },
      },
    },
    handler: async (request, h) => {
      try {
        const result = await Bicycle.findByIdAndUpdate(
          request.params.id,
          request.payload,
          { new: true }
        );
        return h.response(result);
      } catch (error) {
        return h.response(error).code(500);
      }
    },
  },
];
