// <auto-generated>
/*
 * OpenAPI Petstore
 *
 * This spec is mainly for testing Petstore server and contains fake endpoints, models. Please do not use this for any other purpose. Special characters: \" \\
 *
 * The version of the OpenAPI document: 1.0.0
 * Generated by: https://github.com/openapitools/openapi-generator.git
 */

using System;
using System.Collections;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.IO;
using System.Text;
using System.Text.RegularExpressions;
using System.Text.Json;
using System.Text.Json.Serialization;
using System.ComponentModel.DataAnnotations;
using OpenAPIClientUtils = Org.OpenAPITools.Client.ClientUtils;

namespace Org.OpenAPITools.Model
{
    /// <summary>
    /// Order
    /// </summary>
    public partial class Order : IValidatableObject
    {
        /// <summary>
        /// Initializes a new instance of the <see cref="Order" /> class.
        /// </summary>
        /// <param name="id">id</param>
        /// <param name="petId">petId</param>
        /// <param name="quantity">quantity</param>
        /// <param name="shipDate">shipDate</param>
        /// <param name="status">Order Status</param>
        /// <param name="complete">complete (default to false)</param>
        [JsonConstructor]
        public Order(long id, long petId, int quantity, DateTime shipDate, StatusEnum status, bool complete = false)
        {
            Id = id;
            PetId = petId;
            Quantity = quantity;
            ShipDate = shipDate;
            Status = status;
            Complete = complete;
        }

        /// <summary>
        /// Order Status
        /// </summary>
        /// <value>Order Status</value>
        public enum StatusEnum
        {
            /// <summary>
            /// Enum Placed for value: placed
            /// </summary>
            Placed = 1,

            /// <summary>
            /// Enum Approved for value: approved
            /// </summary>
            Approved = 2,

            /// <summary>
            /// Enum Delivered for value: delivered
            /// </summary>
            Delivered = 3

        }

        /// <summary>
        /// Returns a StatusEnum
        /// </summary>
        /// <param name="value"></param>
        /// <returns></returns>
        public static StatusEnum StatusEnumFromString(string value)
        {
            if (value == "placed")
                return StatusEnum.Placed;

            if (value == "approved")
                return StatusEnum.Approved;

            if (value == "delivered")
                return StatusEnum.Delivered;

            throw new NotImplementedException($"Could not convert value to type StatusEnum: '{value}'");
        }

        /// <summary>
        /// Returns equivalent json value
        /// </summary>
        /// <param name="value"></param>
        /// <returns></returns>
        /// <exception cref="NotImplementedException"></exception>
        public static string StatusEnumToJsonValue(StatusEnum value)
        {
            if (value == StatusEnum.Placed)
                return "placed";

            if (value == StatusEnum.Approved)
                return "approved";

            if (value == StatusEnum.Delivered)
                return "delivered";

            throw new NotImplementedException($"Value could not be handled: '{value}'");
        }

        /// <summary>
        /// Order Status
        /// </summary>
        /// <value>Order Status</value>
        [JsonPropertyName("status")]
        public StatusEnum Status { get; set; }

        /// <summary>
        /// Gets or Sets Id
        /// </summary>
        [JsonPropertyName("id")]
        public long Id { get; set; }

        /// <summary>
        /// Gets or Sets PetId
        /// </summary>
        [JsonPropertyName("petId")]
        public long PetId { get; set; }

        /// <summary>
        /// Gets or Sets Quantity
        /// </summary>
        [JsonPropertyName("quantity")]
        public int Quantity { get; set; }

        /// <summary>
        /// Gets or Sets ShipDate
        /// </summary>
        /// <example>&quot;2020-02-02T20:20:20.000222Z&quot;</example>
        [JsonPropertyName("shipDate")]
        public DateTime ShipDate { get; set; }

        /// <summary>
        /// Gets or Sets Complete
        /// </summary>
        [JsonPropertyName("complete")]
        public bool Complete { get; set; }

        /// <summary>
        /// Gets or Sets additional properties
        /// </summary>
        [JsonExtensionData]
        public Dictionary<string, JsonElement> AdditionalProperties { get; } = new Dictionary<string, JsonElement>();

        /// <summary>
        /// Returns the string presentation of the object
        /// </summary>
        /// <returns>String presentation of the object</returns>
        public override string ToString()
        {
            StringBuilder sb = new StringBuilder();
            sb.Append("class Order {\n");
            sb.Append("  Id: ").Append(Id).Append("\n");
            sb.Append("  PetId: ").Append(PetId).Append("\n");
            sb.Append("  Quantity: ").Append(Quantity).Append("\n");
            sb.Append("  ShipDate: ").Append(ShipDate).Append("\n");
            sb.Append("  Status: ").Append(Status).Append("\n");
            sb.Append("  Complete: ").Append(Complete).Append("\n");
            sb.Append("  AdditionalProperties: ").Append(AdditionalProperties).Append("\n");
            sb.Append("}\n");
            return sb.ToString();
        }

        /// <summary>
        /// To validate all properties of the instance
        /// </summary>
        /// <param name="validationContext">Validation context</param>
        /// <returns>Validation Result</returns>
        IEnumerable<System.ComponentModel.DataAnnotations.ValidationResult> IValidatableObject.Validate(ValidationContext validationContext)
        {
            yield break;
        }
    }

    /// <summary>
    /// A Json converter for type Order
    /// </summary>
    public class OrderJsonConverter : JsonConverter<Order>
    {
        /// <summary>
        /// The format to use to serialize ShipDate
        /// </summary>
        public static string ShipDateFormat { get; set; } = "yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'fffffffK";

        /// <summary>
        /// A Json reader.
        /// </summary>
        /// <param name="utf8JsonReader"></param>
        /// <param name="typeToConvert"></param>
        /// <param name="jsonSerializerOptions"></param>
        /// <returns></returns>
        /// <exception cref="JsonException"></exception>
        public override Order Read(ref Utf8JsonReader utf8JsonReader, Type typeToConvert, JsonSerializerOptions jsonSerializerOptions)
        {
            int currentDepth = utf8JsonReader.CurrentDepth;

            if (utf8JsonReader.TokenType != JsonTokenType.StartObject && utf8JsonReader.TokenType != JsonTokenType.StartArray)
                throw new JsonException();

            JsonTokenType startingTokenType = utf8JsonReader.TokenType;

            long id = default;
            long petId = default;
            int quantity = default;
            DateTime shipDate = default;
            Order.StatusEnum status = default;
            bool complete = default;

            while (utf8JsonReader.Read())
            {
                if (startingTokenType == JsonTokenType.StartObject && utf8JsonReader.TokenType == JsonTokenType.EndObject && currentDepth == utf8JsonReader.CurrentDepth)
                    break;

                if (startingTokenType == JsonTokenType.StartArray && utf8JsonReader.TokenType == JsonTokenType.EndArray && currentDepth == utf8JsonReader.CurrentDepth)
                    break;

                if (utf8JsonReader.TokenType == JsonTokenType.PropertyName && currentDepth == utf8JsonReader.CurrentDepth - 1)
                {
                    string propertyName = utf8JsonReader.GetString();
                    utf8JsonReader.Read();

                    switch (propertyName)
                    {
                        case "id":
                            if (utf8JsonReader.TokenType != JsonTokenType.Null)
                                id = utf8JsonReader.GetInt64();
                            break;
                        case "petId":
                            if (utf8JsonReader.TokenType != JsonTokenType.Null)
                                petId = utf8JsonReader.GetInt64();
                            break;
                        case "quantity":
                            if (utf8JsonReader.TokenType != JsonTokenType.Null)
                                quantity = utf8JsonReader.GetInt32();
                            break;
                        case "shipDate":
                            if (utf8JsonReader.TokenType != JsonTokenType.Null)
                                shipDate = JsonSerializer.Deserialize<DateTime>(ref utf8JsonReader, jsonSerializerOptions);
                            break;
                        case "status":
                            string statusRawValue = utf8JsonReader.GetString();
                            status = Order.StatusEnumFromString(statusRawValue);
                            break;
                        case "complete":
                            if (utf8JsonReader.TokenType != JsonTokenType.Null)
                                complete = utf8JsonReader.GetBoolean();
                            break;
                        default:
                            break;
                    }
                }
            }

#pragma warning disable CS0472 // The result of the expression is always the same since a value of this type is never equal to 'null'
#pragma warning disable CS8073 // The result of the expression is always the same since a value of this type is never equal to 'null'

            if (id == null)
                throw new ArgumentNullException(nameof(id), "Property is required for class Order.");

            if (petId == null)
                throw new ArgumentNullException(nameof(petId), "Property is required for class Order.");

            if (quantity == null)
                throw new ArgumentNullException(nameof(quantity), "Property is required for class Order.");

            if (shipDate == null)
                throw new ArgumentNullException(nameof(shipDate), "Property is required for class Order.");

            if (status == null)
                throw new ArgumentNullException(nameof(status), "Property is required for class Order.");

            if (complete == null)
                throw new ArgumentNullException(nameof(complete), "Property is required for class Order.");

#pragma warning restore CS0472 // The result of the expression is always the same since a value of this type is never equal to 'null'
#pragma warning restore CS8073 // The result of the expression is always the same since a value of this type is never equal to 'null'

            return new Order(id, petId, quantity, shipDate, status, complete);
        }

        /// <summary>
        /// A Json writer
        /// </summary>
        /// <param name="writer"></param>
        /// <param name="order"></param>
        /// <param name="jsonSerializerOptions"></param>
        /// <exception cref="NotImplementedException"></exception>
        public override void Write(Utf8JsonWriter writer, Order order, JsonSerializerOptions jsonSerializerOptions)
        {
            writer.WriteStartObject();

            writer.WriteNumber("id", order.Id);
            writer.WriteNumber("petId", order.PetId);
            writer.WriteNumber("quantity", order.Quantity);
            writer.WriteString("shipDate", order.ShipDate.ToString(ShipDateFormat));
            var statusRawValue = Order.StatusEnumToJsonValue(order.Status);
            if (statusRawValue != null)
                writer.WriteString("status", statusRawValue);
            else
                writer.WriteNull("status");
            writer.WriteBoolean("complete", order.Complete);

            writer.WriteEndObject();
        }
    }

}
